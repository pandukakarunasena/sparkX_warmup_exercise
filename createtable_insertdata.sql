create database sparkx_pet;

-- select the created database
use sparkx_pet;

-- create tables owner, pet, procedure_detail, procedure_history
create table owner (
	owner_id int NOT NULL,
	name varchar(20),
    surname varchar(20),
    street_address varchar(50),
    city varchar(30),
	state varchar(20),
    state_full varchar(30),
    zip_code int, 
    PRIMARY KEY (owner_id)
);

describe owner;

create table pet (
	pet_id varchar(10) NOT NULL,
    name varchar(20) NOT NULL,
    kind varchaR(20),
    gender varchar(10),
    age int,
    owner_id int,
    PRIMARY KEY (pet_id),
    FOREIGN KEY (owner_id) REFERENCES owner(owner_id) 
);

-- altering the foreign key to delete and update when the parent table changes 
alter table pet 
add FOREIGN KEY (owner_id) REFERENCES owner(owner_id) ON DELETE CASCADE ON UPDATE CASCADE;

describe pet;

create table procedure_detail(
	procedure_type varchar(50) NOT NULL,
    procedure_subcode int NOT NULL,
    description varchar(50),
    price decimal,
    PRIMARY KEY(procedure_type, procedure_subcode)
);

describe procedure_detail;

-- full detailed table view
show full columns from procedure_detail;

create table procedure_history(
	pet_id varchar(10) NOT NULL,
    date date,
    procedure_type varchar(50) NOT NULL,
    procedure_subcode int NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pet(pet_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (procedure_type, procedure_subcode) REFERENCES procedure_detail(procedure_type, procedure_subcode)
);

-- to easily add the data to the procedure_history table added a primary key column with an auto_incremented id
alter table procedure_history add pro_id int NOT NULL;
alter table procedure_history add PRIMARY KEY (pro_id);
ALTER TABLE procedure_history MODIFY pro_id INTEGER NOT NULL AUTO_INCREMENT;

-- double check the tables
describe procedure_history;
show full columns from procedure_history;
show tables;

-- import data from csv file
select * from owner;
select * from pet;
select * from procedure_detail;
select * from procedure_history;

/*question 01*/
select procedure_type, procedure_subcode 
from procedure_detail
where procedure_detail.price > 150;

/*question 02*/
select owner.name
from owner
inner join pet on pet.owner_id = owner.owner_id
where pet.kind = 'parrot';


/*question 03*/
select owner.zip_code, count(owner.owner_id) as 'no of owners'
from owner
group by owner.zip_code;


/*question 04*/
select distinct pet.name, pet.pet_id
from pet
inner join procedure_history
on pet.pet_id = procedure_history.pet_id
where procedure_history.date between '2016-02-01' and '2016-02-29';


/*question 05*/
select procedure_history.procedure_type, procedure_history.procedure_subcode
from procedure_history
where procedure_history.pet_id in (
	select pet.pet_id
	from pet
	inner join owner
	on pet.owner_id = owner.owner_id
	where owner.zip_code = '49503');
    
    
select sum(procedure_detail.price) as 'total_cost_for_49503'
from procedure_detail
inner join (select procedure_history.procedure_type, procedure_history.procedure_subcode
			from procedure_history
			where procedure_history.pet_id in (
				select pet.pet_id
				from pet
				inner join owner
				on pet.owner_id = owner.owner_id
				where owner.zip_code = '49503')) as pr
on procedure_detail.procedure_type = pr.procedure_type
and procedure_detail.procedure_subcode = pr.procedure_subcode;





