INSERT INTO public.priority
(id, value)
VALUES(nextval('priority_id_seq'::regclass), 'High');


INSERT INTO public.priority
(id, value)
VALUES(nextval('priority_id_seq'::regclass), 'Medium');

INSERT INTO public.priority
(id, value)
VALUES(nextval('priority_id_seq'::regclass), 'Low');



INSERT INTO public.status
(id, value)
VALUES(nextval('status_id_seq'::regclass), 'in-progress');
INSERT INTO public.status
(id, value)
VALUES(nextval('status_id_seq'::regclass), 'done');
INSERT INTO public.status
(id, value)
VALUES(nextval('status_id_seq'::regclass), 'draft');


INSERT INTO public.roles
(id, value)
VALUES(nextval('roles_id_seq'::regclass), 'ADMIN');


INSERT INTO public.roles
(id, value)
VALUES(nextval('roles_id_seq'::regclass), 'USER');