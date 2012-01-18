create or replace function timestamp_to_millis (p in date) 
    return number 
is
    base_point constant timestamp := to_timestamp('01-JAN-1970 00:00:00.000', 'DD-MON-YYYY HH24:MI:SS.FF');
begin
    return round((
                  ((extract(day    from (p-base_point)))*86400)
                + ((extract(hour   from (p-base_point)))*3600)
                + ((extract(minute from (p-base_point)))*60)
                + ((extract(second from (p-base_point))))
           ) * 1000);
end;
/

create or replace function current_millis 
    return number 
is
    base_point constant timestamp := to_timestamp('01-JAN-1970 00:00:00.000', 'DD-MON-YYYY HH24:MI:SS.FF');
    now constant timestamp := systimestamp AT TIME ZONE 'UTC' ;
begin
    return round((
                  ((extract(day    from (now-base_point)))*86400)
                + ((extract(hour   from (now-base_point)))*3600)
                + ((extract(minute from (now-base_point)))*60)
                + ((extract(second from (now-base_point))))
           ) * 1000);
end;
/

CREATE OR REPLACE FUNCTION ida_distance(
	lat1 IN NUMBER,
	lng1 IN NUMBER,
	lat2 IN NUMBER,
	lng2 IN NUMBER)
RETURN NUMBER
IS
	theta	NUMBER;
	dist	NUMBER;
	pi		NUMBER := 3.14159265358979;
BEGIN
  	theta := lng1-lng2;
	dist := SIN(pi/180.0*lat1)*SIN(pi/180.0*lat2)+COS(pi/180.0*lat1)*COS(pi/180.0*lat2)*COS(pi/180.0*theta);
	dist := ACOS(dist);
	dist := 180.0*dist/pi;
	dist := dist * 60.0 * 1.1515 * 1.609344;
	RETURN dist;
END;
/
