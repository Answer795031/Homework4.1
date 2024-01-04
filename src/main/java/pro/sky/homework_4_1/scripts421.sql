ALTER TABLE public.student ADD CONSTRAINT student_age_greater_then_sixteen CHECK (age >= 16);
ALTER TABLE public.student ADD CONSTRAINT student_name_unique UNIQUE (name);
ALTER TABLE public.student ADD "name" varchar(255) NOT NULL;
ALTER TABLE public.faculty ADD CONSTRAINT faculty_unique_name_color UNIQUE (name, color);
ALTER TABLE public.student ADD "name" varchar(255) NOT NULL;