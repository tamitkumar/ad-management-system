CREATE DATABASE ad_system;
use ad_system;

CREATE TABLE clients (
    client_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    contact_info VARCHAR(255)
);

CREATE TABLE campaigns (
     campaign_id BIGINT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     start_time TIMESTAMP,
     end_time TIMESTAMP,
     active BOOLEAN,
     client_id BIGINT,
     CONSTRAINT fk_campaign_client FOREIGN KEY (client_id) REFERENCES clients(client_id)
);


CREATE TABLE campaigns_platforms (
     campaign_entity_campaign_id BIGINT NOT NULL,
     platforms VARCHAR(255) NOT NULL,
     FOREIGN KEY (campaign_entity_campaign_id) REFERENCES campaigns(campaign_id)
);

CREATE TABLE ads (
     ad_id BIGINT PRIMARY KEY AUTO_INCREMENT,
     duration_seconds INT NOT NULL,
     scheduled_start TIMESTAMP NOT NULL,
     scheduled_end TIMESTAMP NOT NULL,
     created_at TIMESTAMP,
     campaign_id BIGINT,
     CONSTRAINT fk_ad_campaign FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);

CREATE TABLE platforms (
     platform_id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE ad_platforms (
     ad_id BIGINT NOT NULL,
     platform_id INT NOT NULL,
     PRIMARY KEY (ad_id, platform_id),
     FOREIGN KEY (ad_id) REFERENCES ads(ad_id),
     FOREIGN KEY (platform_id) REFERENCES platforms(platform_id)
);

CREATE TABLE ad_status (
     ad_id BIGINT NOT NULL,
     platform_id INT NOT NULL,
     status VARCHAR(20) NOT NULL,
     last_updated TIMESTAMP,
     PRIMARY KEY (ad_id, platform_id),
     FOREIGN KEY (ad_id) REFERENCES ads(ad_id),
     FOREIGN KEY (platform_id) REFERENCES platforms(platform_id)
);

select * from cqrs.product_command;
select * from cqrs.product_outbox;
select * from cqrs.product_query;
TRUNCATE TABLE cqrs.product_outbox;
TRUNCATE TABLE cqrs.product_query;

select * from auth.users oc join auth.user_roles ur on (oc.id = ur.user_id)
join auth.roles r on(r.id= ur.role_id);
select * from auth.roles;
select * from auth.user_roles;
select * from auth.oauth_clients oc join auth.roles r on (oc.id = r.id);
select * from auth.oauth_clients;
select * from auth.registered_services;
select * from auth.scope_requests;
CREATE TABLE auth.registered_services (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          service_name VARCHAR(255) NOT NULL UNIQUE,
                                          approved BOOLEAN DEFAULT FALSE
);

CREATE TABLE auth.scope_requests (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     scope VARCHAR(255),
                                     approved BOOLEAN DEFAULT FALSE,
                                     service_id BIGINT,
                                     CONSTRAINT fk_scope_requests_service
                                         FOREIGN KEY (service_id)
                                             REFERENCES auth.registered_services(id)
                                             ON DELETE CASCADE
);

show variables like 'transaction_isolation';