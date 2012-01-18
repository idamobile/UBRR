create or replace view ida_news as
select 
	news_id, 
	news_title as title, 
	news_short as preview, 
	news_text as html_body, 
	null as url, 
	null as category, 
	timestamp_to_millis(news_date) as creation_date
from site_news;

