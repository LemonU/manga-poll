# manga-poll

## User Story

You just ended your shift, and want to quickly checkout the latest manga updates.
However, there are 2 problems here:
- There are hundreds of other manga updates per day, and it's time-consuming to find the ones you are following
- Sometimes the servers are crappy and it takes too long to load the images fast enough to ensure a smooth reading experience

## Objectives

This project will solve the above 2 problems with the following features:
- Allow user to subscribe to mangas
- Notify the user when new updates are available for their subscribed series
- Download the manga to the local disk to allow offline smooth consumption, no need to wait for the crappy servers
- Record the reading progress, take your own pace in consuming the latest releases

## Constraints

The project must utilize a Kafka source connector to pull image from the host and/or a Kafka sink connector to save the images to the local disk.
