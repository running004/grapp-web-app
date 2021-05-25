CREATE TABLE IF NOT EXISTS USUARIOS(email VARCHAR(10), contraseña VARCHAR(64))

CREATE TABLE public.prendas
(
    nombre character varying(50) NOT NULL,
    imgurl character varying(100),
    descripcion character varying(280),
    propietario character varying(40) NOT NULL,
    CONSTRAINT prendas_pkey PRIMARY KEY (propietario, nombre),
    CONSTRAINT prendas_propietario_fkey FOREIGN KEY (propietario)
        REFERENCES public.usuarios (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE public.usuarios
(
    email character varying(40) NOT NULL,
    "contraseña" character varying(64),
    CONSTRAINT usuarios_pkey PRIMARY KEY (email),
    CONSTRAINT usuarios_email_key UNIQUE (email)
)