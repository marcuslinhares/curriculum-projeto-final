CREATE TABLE "curriculum"."usuario" (
  "id" uuid PRIMARY KEY,
  "nome" varchar(255) NOT NULL,
  "email" varchar(126) UNIQUE NOT NULL,
  "senha" varchar(255) NOT NULL,
  "created_at" timestamp NOT NULL,
  "regra_id" uuid NOT NULL
);

ALTER TABLE "curriculum"."usuario" ADD FOREIGN KEY ("regra_id") REFERENCES "curriculum"."regra" ("id") ON DELETE CASCADE;