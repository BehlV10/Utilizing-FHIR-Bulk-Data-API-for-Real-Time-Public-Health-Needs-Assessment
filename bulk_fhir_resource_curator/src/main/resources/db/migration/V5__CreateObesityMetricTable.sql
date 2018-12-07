create table obesity_metrics
(
  id           int auto_increment
    primary key,
  gender       varchar(255)                           not null,
  underweight  int                                    not null,
  healthy      int                                    not null,
  overweight   int                                    not null,
  obese        int                                    not null,
  last_updated datetime default '2000-01-01 00:00:00'  not null
);