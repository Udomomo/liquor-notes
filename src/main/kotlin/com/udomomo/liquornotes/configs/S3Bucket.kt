package com.udomomo.liquornotes.configs

import software.amazon.awssdk.services.s3.S3Client

class S3Bucket(val s3Client: S3Client, val bucket: String)
