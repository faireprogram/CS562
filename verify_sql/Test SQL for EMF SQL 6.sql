drop view IF EXISTS v1 CASCADE;
drop view IF EXISTS v2 CASCADE;

create view v1(cust, prod, avg_1_quant) as 
select cust, prod, avg(quant)
from sales
group by cust, prod;

create view v2(prod, avg_2_quant) as 
select prod, avg(quant)
from sales
group by prod;

select v1.cust, v1.prod, v1.avg_1_quant, v2.avg_2_quant
from v1, v2
where v1.prod = v2.prod;
