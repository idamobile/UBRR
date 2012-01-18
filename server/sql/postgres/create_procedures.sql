CREATE OR REPLACE FUNCTION ida_distance(
	lat1 decimal(10, 7),
	lng1 decimal(10, 7),
	lat2 decimal(10, 7),
	lng2 decimal(10, 7))
RETURNS decimal(10, 7)
AS $$
BEGIN
  RETURN sqrt((lat1-lat2)*(lat1-lat2)+(lng1-lng2)*(lng1-lng2));
END;
$$ LANGUAGE plpgsql;