drop view IF EXISTS h1 CASCADE;
drop view IF EXISTS h2 CASCADE;

create view h1(prod, year, month, sum_1_quant) as
select s1.prod, s1.year, s1.month, sum(s1.quant) as sum_1_quant
from 
(select prod, month, year, quant
from sales
where prod='Butter' and year > 1995) as s1
group by s1.prod, s1.month, s1.year;


create view h2(prod, year, month, sum_2_quant) as
select s2.prod, s2.year, s2.month, sum(s1.quant) as sum_2_quant
from 
(select prod, month, year, quant
from sales
where prod='Butter' and year > 1995) as s1
join
(select  prod, month, year, avg(quant) as avg_0_quant
from sales
group by prod, month, year) as s2
on
s1.prod= s2.prod and s1.year=s2.year and s1.quant>s2.avg_0_quant
group by s2.prod, s2.month, s2.year;



select h1.prod, h1.year,h1.month, h1.sum_1_quant, h2.sum_2_quant, cast(h1.sum_1_quant as double precision)/  h2.sum_2_quant
from h1 left join h2
on 
h1.prod = h2.prod and h1.year = h2.year and h1.month = h2.month;