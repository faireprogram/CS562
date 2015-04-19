drop view IF EXISTS v1 CASCADE;
drop view IF EXISTS v2 CASCADE;
drop view IF EXISTS v3 CASCADE;

create view v1(prod, year, month, avg_0_quant) as
select prod, year, month, avg(quant)
from sales
where sales.year > 1995 and prod='Butter'
group by prod, month, year;

create view v2(prod, year, month, avg_1_quant, sum_2_quant) as
select v1.prod, v1.year, v1.month, avg(sales.quant), sum(sales.quant)
from sales join v1 on
sales.year=v1.year and sales.prod=v1.prod and sales.quant > v1.avg_0_quant
where sales.year > 1995 and sales.prod='Butter'
group by v1.prod, v1.year, v1.month;

create view v3(prod, year, month, sum_3_quant) as
select  f2.prod, f2.year, f2.month, sum(sales.quant)
from 
(
select v1.prod, v1.year, v1.month, coalesce(v2.avg_1_quant, 0) as avg_1_quant , coalesce(v2.sum_2_quant, 0) as sum_2_quant
from v1 left join v2 on
v1.prod=v2.prod and v1.month =v2.month and v1.year=v2.year
)  as  f2 join
sales 
 on
sales.year=f2.year and sales.prod=f2.prod and sales.month=f2.month and sales.quant > f2.avg_1_quant
where sales.year > 1995 and sales.prod='Butter'
group by f2.prod, f2.year, f2.month;


select v1.prod, v1.year, v1.month, coalesce(v2.sum_2_quant, 0), coalesce(v3.sum_3_quant, 0), v2.sum_2_quant/v3.sum_3_quant
from v1 left join v2 on
v1.prod=v2.prod and v1.year=v2.year and v1.month=v2.month left join v3 on
v1.prod=v3.prod and v1.year=v3.year and v1.month=v3.month;



