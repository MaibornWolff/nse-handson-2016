FROM java:8u77-alpine
COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh
ENV JAVA_DEBUG true
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 5005
ENV SERVICE_CHECK_HTTP=/health
ENV SERVICE_CHECK_INTERVAL=5s
COPY badge-reader.jar /