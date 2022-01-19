-- SQL Manager for PostgreSQL 6.3.1.54838
-- ---------------------------------------
-- Host      : localhost
-- Database  : sweetvillas
-- Version   : PostgreSQL 13.3 (Ubuntu 13.3-1.pgdg20.04+1) on x86_64-pc-linux-gnu, compiled by gcc (Ubuntu 9.3.0-17ubuntu1~20.04) 9.3.0, 64-bit


SET check_function_bodies = false;
--
-- Structure for table accounts (OID = 30935) : 
--
SET search_path = public, pg_catalog;
CREATE TABLE public.accounts (
    account_id serial NOT NULL,
    email varchar(256),
    pwd varchar(256),
    token varchar(255),
    token_expiration timestamp without time zone,
    enabled boolean DEFAULT true,
    created_date timestamp(0) without time zone DEFAULT now(),
    updated_date timestamp(0) without time zone
)
WITH (oids = false);
--
-- Structure for table cities (OID = 30946) : 
--
CREATE TABLE public.cities (
    city_id serial NOT NULL,
    city varchar(128),
    country_id integer,
    state_id integer
)
WITH (oids = false);
--
-- Structure for table countries (OID = 30950) : 
--
CREATE TABLE public.countries (
    country_id integer DEFAULT nextval(('public.countries_country_id_seq'::text)::regclass) NOT NULL,
    country varchar(128),
    abbr varchar(15)
)
WITH (oids = false);
--
-- Structure for table roles (OID = 30954) : 
--
CREATE TABLE public.roles (
    role_id varchar(3) NOT NULL,
    role varchar(50)
)
WITH (oids = false);
--
-- Structure for table specs (OID = 30957) : 
--
CREATE TABLE public.specs (
    spec_id varchar(36) DEFAULT upper((uuid_in((md5(((random())::text || (clock_timestamp())::text)))::cstring))::text) NOT NULL,
    spec varchar(125),
    created_date timestamp(0) without time zone DEFAULT now()
)
WITH (oids = false);
--
-- Structure for table states (OID = 30964) : 
--
CREATE TABLE public.states (
    state_id serial NOT NULL,
    state varchar(128)
)
WITH (oids = false);
--
-- Structure for table users (OID = 30968) : 
--
CREATE TABLE public.users (
    user_id varchar(36) DEFAULT (uuid_in((md5(((random())::text || (clock_timestamp())::text)))::cstring))::text NOT NULL,
    surname varchar(128),
    name varchar(128),
    fiscal_code varchar(128),
    account_id integer,
    role_id varchar(3),
    enabled boolean DEFAULT true,
    created_date timestamp(0) without time zone DEFAULT now(),
    updated_date timestamp(0) without time zone,
    account bytea
)
WITH (oids = false);
--
-- Structure for table users_specs (OID = 30977) : 
--
CREATE TABLE public.users_specs (
    user_id varchar(36) NOT NULL,
    spec_id varchar(36) NOT NULL
)
WITH (oids = false);
--
-- Definition for index users_fiscal_code_idx (OID = 30980) : 
--
CREATE UNIQUE INDEX users_fiscal_code_idx ON public.users USING btree (fiscal_code);
--
-- Definition for index accounts_email_key (OID = 30981) : 
--
ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_email_key
    UNIQUE (email);
--
-- Definition for index accounts_pkey (OID = 30983) : 
--
ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_pkey
    PRIMARY KEY (account_id);
--
-- Definition for index cities_pkey (OID = 30985) : 
--
ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_pkey
    PRIMARY KEY (city_id);
--
-- Definition for index countries_pkey (OID = 30987) : 
--
ALTER TABLE ONLY countries
    ADD CONSTRAINT countries_pkey
    PRIMARY KEY (country_id);
--
-- Definition for index roles_pkey (OID = 30989) : 
--
ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey
    PRIMARY KEY (role_id);
--
-- Definition for index specs_pkey (OID = 30991) : 
--
ALTER TABLE ONLY specs
    ADD CONSTRAINT specs_pkey
    PRIMARY KEY (spec_id);
--
-- Definition for index states_pkey (OID = 30993) : 
--
ALTER TABLE ONLY states
    ADD CONSTRAINT states_pkey
    PRIMARY KEY (state_id);
--
-- Definition for index users2specs_pkey (OID = 30995) : 
--
ALTER TABLE ONLY users_specs
    ADD CONSTRAINT users2specs_pkey
    PRIMARY KEY (user_id, spec_id);
--
-- Definition for index users_pkey (OID = 30997) : 
--
ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey
    PRIMARY KEY (user_id);
--
-- Definition for index cities_country_id_fk (OID = 30999) : 
--
ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fk
    FOREIGN KEY (country_id) REFERENCES countries(country_id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index cities_state_id_fk (OID = 31004) : 
--
ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_state_id_fk
    FOREIGN KEY (state_id) REFERENCES states(state_id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index spec_id_fk (OID = 31009) : 
--
ALTER TABLE ONLY users_specs
    ADD CONSTRAINT spec_id_fk
    FOREIGN KEY (spec_id) REFERENCES specs(spec_id) ON UPDATE CASCADE;
--
-- Definition for index user_id_fk (OID = 31014) : 
--
ALTER TABLE ONLY users_specs
    ADD CONSTRAINT user_id_fk
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index users_account_id_fk (OID = 31019) : 
--
ALTER TABLE ONLY users
    ADD CONSTRAINT users_account_id_fk
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index users_role_id (OID = 31024) : 
--
ALTER TABLE ONLY users
    ADD CONSTRAINT users_role_id
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON UPDATE CASCADE;
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
