# 🌐 Twenty-First-Century

A modern Java-based tool for downloading issues of the *Twenty-First Century* journal. This project automates the download of full-text PDFs from the official website, which offers free access but does not support batch downloading.

> 📰 "Twenty-First Century" is my favorite journal. Thanks to the Chinese University of Hong Kong for continuing to publish this excellent work.

![Build](https://github.com/maodunluo/Twenty-First-Century/actions/workflows/test.yml/badge.svg)
[![codecov](https://codecov.io/gh/maodunluo/Twenty-First-Century/branch/master/graph/badge.svg)](https://codecov.io/gh/maodunluo/Twenty-First-Century)
![License](https://img.shields.io/github/license/maodunluo/Twenty-First-Century)

---

## ✨ Features

- 📥 Download full issues of *Twenty-First Century* directly to your local folder
- 🆓 Supports the journal’s free public access policy
- 🔢 Input the issue number you want to download
- 🚫 Bypasses manual click-through; enables semi-automated retrieval
- 📁 Saves PDFs in the same folder as your `.jar` package

---

## 🌐 Official Website

You can freely browse or manually download journal issues here:
[**Twenty-First Century – CUHK Journal**](https://www.cuhk.edu.hk/ics/21c/)

---

## 🚀 How to Use

1. Download or clone this repository
2. Build the jar with Maven:
   ```bash
   mvn clean package
   ```
3. Run the jar file and input the issue number:
   ```bash
   java -jar target/twenty-first-century.jar
   ```
4. The selected issue PDF will be downloaded to the same folder

---

## 📁 Project Structure

```bash
Twenty-First-Century/
├── src/
│   └── main/java/com/downloader/       # Core logic for fetching journal PDFs
├── pom.xml                            # Maven configuration
└── README.md                          # You're here!
```

---

## 🧩 Contributions

If you have ideas for improvements (batch downloading, UI, proxy support), feel free to fork this repo and submit a pull request!

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for more info.

---

## ⭐ Support

If you find this tool helpful, feel free to star this project to support its continued maintenance.
