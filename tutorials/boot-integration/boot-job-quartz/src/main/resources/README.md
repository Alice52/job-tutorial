## notice

### about yml usage
1. `application-durable-with-main-database.yml` must comment `QuartzConfiguration` to use  spring.database.* config to use main database.

2. `application-durable-with-self-database.yml` must enable `QuartzConfiguration`, which is specify quartz database.

3.  `application-cluster-*.yml` will use single database same as `self-database`


### about job config/init

1. when test specified case, please comment other job-init or job config.