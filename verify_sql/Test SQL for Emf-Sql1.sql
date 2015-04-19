drop view IF EXISTS h1 CASCADE;
drop view IF EXISTS h2 CASCADE;

create view h1(prod, month, avg_1_quant) as
select a.prod, a.month, avg(b.quant)
 from
(select distinct prod, year,month,quant
from sales
where year=1997 and prod = 'Milk') as a
left join
(select distinct prod, year,month,quant
from sales
where year=1997) as b
on a.month=b.month+1
and a.prod=b.prod
group by a.prod, a.month;


create view h2(prod, month, avg_1_quant) as
select a.prod, a.month, avg(b.quant)
 from
(select distinct prod, year,month,quant
from sales
where year=1997 and prod = 'Milk') as a
left join
(select distinct prod, year,month,quant
from sales
where year=1997) as b
on a.month=b.month-1
and a.prod=b.prod
group by a.prod, a.month;


select h1.prod, h1.month, h1.avg_1_quant, h2.avg_1_quant, h1.avg_1_quant/h2.avg_1_quant
from h1 join h2 on h1.prod = h2.prod and h1.month = h2.month ;
