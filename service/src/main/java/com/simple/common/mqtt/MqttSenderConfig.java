package com.simple.common.mqtt;



import org.apache.commons.lang.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.ExecutionException;

@Configuration
@IntegrationComponentScan
public class MqttSenderConfig {
    @Value("${spring.mqtt.server.username}")
    private String username;
    @Value("${spring.mqtt.server.password}")
    private String password;
    @Value("${spring.mqtt.server.url}")
    private String hostUrl;
    @Value("${spring.mqtt.client.publishId}")
    private String publishClientId;
    @Value("${spring.mqtt.client.subscribeId}")
    private String subscribeClientId;
    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;
    @Value("${spring.mqtt.default.completionTimeout}")
    private int completionTimeout;

    @Autowired
    private MqttProxy mqttProxy;


    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(15);
        mqttConnectOptions.setKeepAliveInterval(90);
        mqttConnectOptions.setAutomaticReconnect(true);
        //mqttConnectOptions.setUserName("admin");
        //mqttConnectOptions.setPassword("public".toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /****************************************发送消息部分********************************************/
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String clientId = publishClientId + "_" +RandomStringUtils.randomAlphanumeric(10);
        System.out.println("MQTT Sender ClientId is " + clientId);
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /****************************************接收消息部分********************************************/
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * @param
     * @return adapter
     * 接收消息 配置及订阅消息
     * @author huangquanguang
     * @date 2020/1/3 16:34
     */
    @Bean
    public MessageProducer inbound() {
        //这里要设置接收的topic,要写成动态添加的方式，后续新增设备时不用设置
        //获取所有要订阅的topic
//        List<TopicDO> topicDOS = topicService.listByMap(map);
        //List<TopicDO> topicDOS = new ArrayList<>();
        //启动项目执行：把所有当前主题保存到redis缓存
        //RedisUtil redisUtil = new RedisUtil();
        //redisUtil.addObject("topics", topicDOS);
        String[] topics = {"ci/simple/center/server/test","tele/#","stat/#"};
        //String[] topics = new String[];
//        if (topicDOS != null && topicDOS.size() > 0) {
//            for (int i = 0; i < topicDOS.size(); i++) {
//                topics[i] = topicDOS.get(i).getTopic();
//            }
//        }
        String clientId = subscribeClientId + "_" +RandomStringUtils.randomAlphanumeric(10);
        System.out.println("MQTT Receiver ClientId is " + clientId);
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,
                mqttClientFactory(), topics);
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        // 设置服务质量
        // 0 最多一次，数据可能丢失;
        // 1 至少一次，数据可能重复;
        // 2 只有一次，有且只有一次;最耗性能
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     *
     *
     * @description 消费消息
     * ServiceActivator注解表明当前方法用于处理MQTT消息，inputChannel参数指定了用于消费消息的channel。
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {

        return message -> {
            String payload = message.getPayload().toString();
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            //用线程池处理订阅到的消息
            try {
                dealMessageByThreadPool(payload, topic);
                System.out.println("topic: " + topic);
                //System.out.println("payload: " + payload);
            } catch (ExecutionException e) {
                e.printStackTrace();
                //log.error("处理订阅出错：" + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * @param
     * @return 多线程处理收到的传感器推送, 线程处理参考：
     * https://www.bbsmax.com/A/mo5kxvPvdw/（这里注释掉线程的使用，保持数据的一致性
     * 在处理某一次上报的数据时可以用多线程，提高效率，不能每次订阅数据都起一个线程让它单独处理）
     * 要根据厂商提交的设备id及主题先把所有设备的主题管理起来，一个设备对应一个或多个主题
     *
     *
     */
    public void dealMessageByThreadPool(String payload, String topic) throws ExecutionException, InterruptedException {



        mqttProxy.handleMessage(topic,payload);

    }

}
