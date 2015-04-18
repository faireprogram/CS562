create view  v1(prod, month, year, sum_1_quant) as
select prod, month, year, sum(quant)
from sales
where prod = 'Butter' and year > 1995
group by prod, month, year

create view v2 (prod, year, sum_2_quant) as
select prod, year, sum(quant)
from sales
where prod = 'Butter' and year > 1995
group by prod, year

select v1.prod, v1.month, v1.year, v1.sum_1_quant, v2.sum_2_quant, cast(v1.sum_1_quant as double precision) / cast (v2.sum_2_quant as double precision)
from v1, v2
where v1.prod = v2.prod and v1.year = v2.year
