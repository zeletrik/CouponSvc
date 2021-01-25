FROM postgres:13.1-alpine
ENV POSTGRES_USER docker
ENV POSTGRES_PASSWORD s3cr3t
ENV POSTGRES_DB coupon
COPY init.sql /docker-entrypoint-initdb.d/
