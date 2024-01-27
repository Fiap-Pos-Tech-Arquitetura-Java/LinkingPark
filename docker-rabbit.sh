apt-get update
apt-get --assume-yes install wget
cd /opt/rabbitmq/plugins/
wget https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.12.0/rabbitmq_delayed_message_exchange-3.12.0.ez
rabbitmq-plugins enable rabbitmq_delayed_message_exchange