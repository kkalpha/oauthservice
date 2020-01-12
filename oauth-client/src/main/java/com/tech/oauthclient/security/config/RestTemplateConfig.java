package com.tech.oauthclient.security.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	/**
	 * 返回RestTemplate
	 * 
	 * @param factory ClientHttpRequestFactory
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}

	/**
	 * ClientHttpRequestFactory接口的另一种实现方式（推荐使用），即：
	 * HttpComponentsClientHttpRequestFactory：底层使用Httpclient连接池的方式创建Http连接请求
	 * 
	 * @return HttpComponentsClientHttpRequestFactory
	 */
	@Bean
	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {

		// Httpclient连接池，长连接保持30秒
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(getRegistry(),null,null,null,30,TimeUnit.SECONDS);
		
		// 设置总连接数
		connectionManager.setMaxTotal(1000);
		// 设置同路由的并发数
		connectionManager.setDefaultMaxPerRoute(1000);

		// 设置header
		List<Header> headers = new ArrayList<Header>();
		headers.add(
				new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.04"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"));
		headers.add(new BasicHeader("Connection", "keep-alive"));
		
		// 创建HttpClient
		HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
				.setDefaultHeaders(headers).setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)) // 设置重试次数
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()) // 设置保持长连接
				.build();

		// 创建HttpComponentsClientHttpRequestFactory实例
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		// 设置客户端和服务端建立连接的超时时间
		requestFactory.setConnectTimeout(500000);
		// 设置客户端从服务端读取数据的超时时间
		requestFactory.setReadTimeout(500000);
		// 设置从连接池获取连接的超时时间，不宜过长
		requestFactory.setConnectionRequestTimeout(200);
		// 缓冲请求数据，默认为true。通过POST或者PUT大量发送数据时，建议将此更改为false，以免耗尽内存
		requestFactory.setBufferRequestBody(false);

		return requestFactory;
	}
	
	private Registry<ConnectionSocketFactory> getRegistry(){
		SSLContextBuilder builder = new SSLContextBuilder();
		SSLConnectionSocketFactory sslsf = null;
		
		// To ignore https certificate verification
        try {
			builder.loadTrustMaterial(null, new TrustStrategy() {
			    @Override
			    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			        return true;
			    }
			});
			
			sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
		
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();
        
        return registry;
	}
}
