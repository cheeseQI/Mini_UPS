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
-- Name: package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.package (
    packageid integer NOT NULL,
    description character varying(255),
    itemnum integer NOT NULL,
    truckid integer,
    userid integer NOT NULL,
    destx integer NOT NULL,
    desty integer NOT NULL,
    whid integer NOT NULL,
    startx integer DEFAULT 0,
    starty integer DEFAULT 0
);


ALTER TABLE public.package OWNER TO postgres;

--
-- Name: package package_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.package
    ADD CONSTRAINT package_pkey PRIMARY KEY (packageid);


--
-- Name: package package_truckid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.package
    ADD CONSTRAINT package_truckid_fkey FOREIGN KEY (truckid) REFERENCES public.truck(truckid);


--
-- Name: package package_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.package
    ADD CONSTRAINT package_userid_fkey FOREIGN KEY (userid) REFERENCES public.user_table(userid);


--
-- PostgreSQL database dump complete
--

