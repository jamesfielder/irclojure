\connect ircdb irc

create table if not exists irclogs
(
	id bigserial not null
		constraint irclogs_pkey
			primary key,
	channel varchar(255) not null,
	nick varchar(255) not null,
	message text,
	inserted timestamp default now()
);

alter table irclogs owner to irc;

create unique index if not exists irclogs_id_uindex
	on irclogs (id);

create index if not exists irclogs_channel
	on irclogs (channel);

create index if not exists irclogs_nick
	on irclogs (nick);

create index if not exists irclogs_inserted
	on irclogs (inserted);