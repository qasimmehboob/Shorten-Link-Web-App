ARG CACHEBUST=1
# Use official nginx image as the base image
FROM nginx:alpine

#Copy config file
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build output to replace the default nginx contents.
COPY /dist/shorten-link-ui /usr/share/nginx/html
