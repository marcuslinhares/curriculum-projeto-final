CREATE TABLE "curriculum"."usuario" (
  "id" uuid PRIMARY KEY,
  "nome" varchar(255) NOT NULL,
  "email" varchar(126) UNIQUE NOT NULL,
  "senha" varchar(255) NOT NULL,
  "created_at" timestamp NOT NULL,
  "role_id" uuid NOT NULL
);