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
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>About DevOps</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background: linear-gradient(135deg, #0f172a, #1e293b);
                        color: #e2e8f0;
                        margin: 0;
                        padding: 0;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        min-height: 100vh;
                    }
                    .container {
                        max-width: 800px;
                        background: rgba(255, 255, 255, 0.05);
                        padding: 40px;
                        border-radius: 16px;
                        box-shadow: 0 8px 20px rgba(0,0,0,0.4);
                        text-align: center;
                    }
                    h1 {
                        font-size: 2.5rem;
                        margin-bottom: 20px;
                    }
                    p {
                        line-height: 1.6;
                        font-size: 1.1rem;
                    }
                    ul {
                        list-style: none;
                        padding: 0;
                        margin: 20px 0;
                    }
                    ul li {
                        margin: 12px 0;
                        font-size: 1.1rem;
                        background: rgba(255, 255, 255, 0.08);
                        padding: 10px 15px;
                        border-radius: 8px;
                        transition: transform 0.2s;
                    }
                    ul li:hover {
                        transform: scale(1.05);
                        background: rgba(255, 255, 255, 0.15);
                    }
                    a {
                        color: cyan;
                        text-decoration: none;
                        margin: 0 10px;
                        font-weight: bold;
                        transition: color 0.3s;
                    }
                    a:hover {
                        color: #38bdf8;
                    }
                    .footer {
                        margin-top: 30px;
                        font-size: 0.9rem;
                        color: #94a3b8;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>ℹ️ About DevOps</h1>
                    <p><b>DevOps</b>, geliştirme ve operasyon ekiplerinin birlikte çalışmasını sağlayan bir kültürdür. 
                    Amacı hızlı, güvenli ve sürekli teslimatı mümkün kılmaktır.</p>
                    
                    <ul>
                        <li>⚡ Sürekli Entegrasyon (CI)</li>
                        <li>🚀 Sürekli Dağıtım (CD)</li>
                        <li>☁️ Altyapı Kod Olarak (IaC)</li>
                        <li>📈 Gözlemlenebilirlik & Monitoring</li>
                    </ul>
                    
                    <p><strong>🕒 Sunucu Saati:</strong> %s</p>
                    
                    <div class="footer">
                        <a href="/">🏠 Dashboard</a> | 
                        <a href="/info">ℹ️ Info</a>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(LocalDateTime.now());
    }

    }


