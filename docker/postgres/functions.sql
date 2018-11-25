create or replace function conditions(lim boolean, timeRange varchar(255)) returns text as $$
begin
  if lim = true then
    return $clause$where inserted <= now() and inserted >= (now() - interval '$clause$ || timeRange || $clause$')$clause$;
  else
    return 'where inserted <= now()';
  end if;
end
$$ language plpgsql;

create or replace function linesPerDay(timeLimit boolean, timeInterval text)
  returns table(day timestamp, count bigint)
as $$
begin
  return query execute 'select date_trunc(''day'', inserted) as day, count(*)
   from irclogs ' || conditions(timeLimit, timeInterval) ||
  ' group by day
  order by day;';
end
$$ language plpgsql;

create or replace function mostActiveNicks(timeLimit boolean, timeInterval text)
  returns table(nick varchar(255), count bigint)
as $$
begin
  return query execute 'select distinct nick, count(*)
   from irclogs ' || conditions(timeLimit, timeInterval) ||
  ' group by nick
  order by count desc;';
end
$$ language plpgsql;

create or replace function activityByHour(timeLimit boolean, timeInterval text)
  returns table(hour double precision, count bigint)
as $$
begin
  return query execute 'select date_part(''hour'', inserted) as hour, count(*)
from irclogs ' || conditions(timeLimit, timeInterval) ||
  ' group by hour
order by hour;';
end
$$ language plpgsql;