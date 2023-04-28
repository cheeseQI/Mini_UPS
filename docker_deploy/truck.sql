--
-- PostgreSQL database dump
--

-- Dumped from database version 12.14 (Ubuntu 12.14-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.14 (Ubuntu 12.14-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: truck; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.truck (
    truckid integer NOT NULL,
    status character varying(50) NOT NULL,
    currx integer NOT NULL,
    curry integer NOT NULL,
    packagenum integer NOT NULL
);


ALTER TABLE public.truck OWNER TO postgres;

--
-- Name: truck truck_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.truck
    ADD CONSTRAINT truck_pkey PRIMARY KEY (truckid);


--
-- PostgreSQL database dump complete
--

