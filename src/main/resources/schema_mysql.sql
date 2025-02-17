 
drop table if exists customer CASCADE;
create table customer (
	id bigint NOT NULL AUTO_INCREMENT,
	name varchar(255), 
	tier integer, 
	primary key (id)
);

drop table if exists forex_rate_booking CASCADE;
create table forex_rate_booking (
	id bigint NOT NULL AUTO_INCREMENT,
	base_currency varchar(255), 
	base_currency_amount decimal(19,2), 
	booking_ref varchar(255), 
	counter_currency varchar(255), 
	expiry_time timestamp, 
	rate double, 
	timestamp timestamp, 
	customer_id bigint, 
	trade_action varchar(255),
	primary key (id)
);

drop table if exists forex_trade_deal CASCADE;
create table forex_trade_deal (
	id bigint NOT NULL AUTO_INCREMENT,
	base_currency varchar(255), 
	base_currency_amount decimal(19,2), 
	counter_currency varchar(255), 
	deal_ref varchar(255), 
	rate double, 
	timestamp timestamp, 
	customer_id bigint, 
	trade_action varchar(255),
	primary key (id)
);

