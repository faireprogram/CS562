create view  h1(cust, avg_1_quant) as
select cust, avg(quant)
from sales
where state = 'NY' and year = 1997
group by cust

create view h2(cust, avg_2_quant) as
select cust, avg(quant)
from sales
where state = 'NJ' and year = 1997
group by cust

create view h3(cust, avg_3_quant) as
select cust, avg(quant)
from sales
where state = 'CT' and year = 1997
group by cust

select h1.cust, h1.avg_1_quant, h2.avg_2_quant, h3.avg_3_quant
from h1, h2, h3
where h1.cust = h2.cust and h2.cust = h3.cust and h1.avg_1_quant > h2.avg_2_quant and h1.avg_1_quant > h3.avg_3_quant
