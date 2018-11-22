FROM alpine:latest
EXPOSE 6667/tcp 6697/tcp

RUN apk add --no-cache wget

RUN mkdir -p /ircd
WORKDIR /ircd

RUN wget https://github.com/oragono/oragono/releases/download/v0.12.0/oragono-0.12.0-linux-x64.tar.gz -O /oragono.tgz
RUN tar -xzf /oragono.tgz
COPY run.sh /ircd
RUN rm /ircd/oragono.yaml
COPY ircd.yaml /ircd

CMD ["./run.sh"]
