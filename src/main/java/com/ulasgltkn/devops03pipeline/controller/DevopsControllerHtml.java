package com.ulasgltkn.devops03pipeline.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/html")
public class DevopsControllerHtml {
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String devopsHello() {
        return """
                <!DOCTYPE html>
                <html lang="tr">
                <head>
                    <meta charset="UTF-8">
                    <title>DevOps Dashboard</title>
                </head>
                <body style="font-family: Arial, sans-serif; text-align: center; background: #0f172a; color: white; padding: 40px;">
                    <h1>🚀 DevOps Dashboard</h1>
                    <p>Merhaba, bu sayfa Spring Boot uygulamasından inline HTML olarak döndürüldü.</p>
                    <p><strong>Sunucu Saati:</strong> %s</p>
                    <p><a href="/info" style="color: cyan;">Info</a> | <a href="/about" style="color: cyan;">About</a></p>
                </body>
                </html>
                """.formatted(LocalDateTime.now());
    }

    // http://localhost:8080/info
    @GetMapping(value = "info", produces = MediaType.TEXT_HTML_VALUE)
    public String info() {
        return """
                <!DOCTYPE html>
                <html lang="tr">
                <head>
                    <meta charset="UTF-8">
                    <title>DevOps Info</title>
                </head>
                <body style="font-family: Arial, sans-serif; background: #1e293b; color: #f1f5f9; padding: 40px;">
                    <h1>📊 DevOps Info</h1>
                    <ul>
                        <li><b>Java Version:</b> 21</li>
                        <li><b>Spring Boot:</b> Uygulama Çalışıyor</li>
                        <li><b>CI/CD Pipeline:</b> Aktif</li>
                        <li><b>Sunucu Saati:</b> %s</li>
                    </ul>
                    <p><a href="/" style="color: cyan;">Dashboard</a> | <a href="/about" style="color: cyan;">About</a></p>
                </body>
                </html>
                """.formatted(LocalDateTime.now());
    }

    // http://localhost:8080/about
    @GetMapping(value = "about", produces = MediaType.TEXT_HTML_VALUE)
    public String about() {
        return """
                <!DOCTYPE html>
                <html lang="tr">
                <head>
                    <meta charset="UTF-8">
                    <title>About DevOps</title>
                </head>
                <body style="font-family: Arial, sans-serif; background: #0f172a; color: #e2e8f0; padding: 40px;">
                    <h1>ℹ️ About DevOps</h1>
                    <p><b>DevOps</b>, geliştirme ve operasyon ekiplerinin birlikte çalışmasını
                    sağlayan bir kültürdür. Hedefi:</p>
                    <ul>
                        <li>⚡ Sürekli Entegrasyon (CI)</li>
                        <li>🚀 Sürekli Dağıtım (CD)</li>
                        <li>☁️ Altyapı Kod Olarak (IaC)</li>
                        <li>📈 Gözlemlenebilirlik & Monitoring</li>
                    </ul>
                    <p><strong>Sunucu Saati:</strong> %s</p>
                    <p><a href="/" style="color: cyan;">Dashboard</a> | <a href="/info" style="color: cyan;">Info</a></p>
                </body>
                </html>
                """.formatted(LocalDateTime.now());
    }
    }


