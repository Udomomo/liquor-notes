FROM mcr.microsoft.com/devcontainers/base:bookworm
RUN apt update; apt install -y wget software-properties-common apt-transport-https git
RUN curl -L "https://github.com/supabase/cli/releases/download/v2.72.7/supabase_linux_arm64.tar.gz" -o supabase.tar.gz && \
    tar -xzf supabase.tar.gz -C /usr/local/bin && \
    rm supabase.tar.gz
