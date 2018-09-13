select count(*) from parser.entry;

## Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
SELECT e.ipAddress, COUNT(*) FROM parser.entry e WHERE e.date BETWEEN '2017-01-01 15:00:00' AND '2017-01-01 15:59:59' GROUP BY e.ipAddress HAVING COUNT(*) > 200;

## Write MySQL query to find requests made by a given IP.
SELECT * FROM parser.entry e WHERE e.ipAddress = "192.168.106.134";

## Write MySQL query to find all restrictions
SELECT * FROM parser.restriction;