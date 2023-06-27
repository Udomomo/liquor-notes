package com.udomomo.liquornotes.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Component
@ConfigurationProperties(prefix = "aws.s3")
class S3ConfigProperties {
    var endpoint: String = ""
    var region: String = ""
    var bucket: String = ""
}

@Configuration
class S3Config(
    private val properties: S3ConfigProperties,
) {
    @Bean
    fun s3Bucket(): S3Bucket {
        val s3Client = S3Client.builder()
            .endpointOverride(URI.create(properties.endpoint))
            .region(Region.of(properties.region))
            .build()

        return S3Bucket(s3Client, properties.bucket)
    }
}
