﻿CREATE DEFINER = 'root'@'localhost' PROCEDURE `sync`()
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN


/*
1) все сформированные поезда к отправке с нашей станции, у которых
время отправления < текущего переводим в статус "В пути" и освобождаем ROAD.

2) все поезда "В пути", отправленные от нашей станции, у которых
время прибытия < текущего переводим в статус "Расформирован".
   Все вагоны из этого поезда переводим в ближайший запланированный к отправке.
 Если в ближайшем запланированном к отправке уже есть вагоны - ставим
 вагоны в статус "неизвестно".

3) все запланированные поезда к отправке из станции назначения, у которых
время отправления < текущего переводим в статус "В Пути".
*/

declare id int default 0;
declare min_id int default 0;
declare done int default 0;

Declare cur Cursor for Select idTrain from ids;
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;

start transaction;

create temporary table ids (idTrain int);

set @t = CURRENT_TIMESTAMP;

/* 1 */
insert ids
select distinct t.`id_train`
from `train` t
	join `shedule` s on t.`id_shedule` = s.`id_shedule`
where s.`id_shedule` in (select r.`shedule_forward` from route r)
	and t.`id_status` = 2 and t.`date_from` < @t;

update train t
set t.`id_status` = 3
where t.`id_train` in (select idTrain from ids);

update car
set id_location = 2 
where car_number in (select car_number from train_det where id_train in
	(select idTrain from ids));

update road_det 
set `road_det`.`id_train` = null
where road_det.`id_train` in (select idTrain from ids);	
        
delete from road_det
where road_det.`id_train` is null and road_det.`car_number` is null;

delete from ids;

/* 2 */

insert ids
select distinct t.`id_train`
from `train` t
	join `shedule` s on t.`id_shedule` = s.`id_shedule`    
where s.`id_shedule` in (select r.`shedule_forward` from route r)
	and t.`id_status` = 3 and t.`date_to` < @t;
    
update train
set train.`id_status` = 5
where train.`id_train` in (select idTrain from ids);


Open cur;
WHILE done = 0 DO
	FETCH cur INTO id;
    
    /* ближайший поезд */
    set min_id := (
    	select min(t.`id_train`) from train t
			join `shedule` s on t.`id_shedule` = s.`id_shedule`
	    	join `route` r on r.`shedule_back` = s.`id_shedule`
    		join `train` tt on r.`shedule_forward` = tt.`id_shedule` 
            	and tt.`id_train` = id
		where t.`id_status` = 1 and t.`date_from` > tt.`date_to`
        );
    
	/* у ближайшего запланированного поезда уже есть вагоны */
    if exists (select * from train_det where id_train = min_id) then
    	
        /* ставим наши в вагоны в статус "неизвестно" */
    	update car
    	set id_location = 1 
    	where car_number in (select car_number from train_det where id_train = id);
    
    /* у поезда нет вагонов, закидываем наши вагоны в него */
	else 
		insert train_det(id_train, car_number, car_number_in_train)
    	select min_id, car_number, car_number_in_train from train_det 
        where id_train = id;
        
        insert `car_history`(`car_number`, `id_location`, `id_train`, `date`) 
		select car_number, 2, id_train, @t from train_det 
        where id_train = min_id;
        
    end if;
    
  
END WHILE;

Close cur; 

delete from ids;


/* 3 */

insert ids
select distinct t.`id_train`
from `train` t
	join `shedule` s on t.`id_shedule` = s.`id_shedule`
where s.`id_shedule` in (select r.`shedule_back` from route r)
	and t.`id_status` = 1 and t.`date_from` < @t;

update train t
set t.`id_status` = 3
where t.`id_train` in (select idTrain from ids);

update car
set id_location = 2 
where car_number in (select car_number from train_det where id_train in
	(select idTrain from ids));

/*insert `log` (comment) values('OK');*/

drop table ids;

commit;

END;