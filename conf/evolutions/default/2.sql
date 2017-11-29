# --- Sample dataset

# --- !Ups

insert into task values (1, 'Arriver au bout du projet Startup', 'Pas de description', 1);
insert into task values (2, 'Nettoyer mon bureau', 'Car il y a beaucoup de poussière', 1);

# --- !Downs

delete from task;
