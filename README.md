# Budget Tracker

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)

An Android application for tracking daily expenses and managing monthly budgets with a modern interface.
## Features

**Transaction Management**
- Add, edit, and delete income and expenses
- Categorize transactions (Food, Travel,Bills,Shopping,etc.)
- View complete transaction history


**Budget Monitoring**
- Set monthly spending limits
- Visual indicators when approaching or exceeding budget
- Real-time spending overview

**Modern UI**
- Built with Jetpack Compose and Material 3
- Clean, intuitive interface
- Dark mode support

**Local Storage**
- All data stored offline using Room Database
- No internet required
- Fast and secure


## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend/UI | Jetpack Compose, Material 3 |
| Architecture | MVVM |
| Local Storage | Room Database |
| Language | Kotlin |
| Tools | Android Studio, Gradle |

## Project Structure

```
BudgetTracker/
│
├── data/
│   ├── dao/              # Database Access Objects
│   ├── entities/         # Room entities
│   └── database/         # Database configuration
│
├── ui/
│   ├── screens/          # Compose screens
│   ├── components/       # Reusable components
│   └── theme/            # App theme
│
└── viewmodel/            # ViewModels
```

## Installation

1. Clone the repository
   ```bash
   git clone https://github.com/shwetamishra21/BudgetTracker.git
   ```

2. Open the project in Android Studio

3. Build and run the app
   ```
   Run → Run 'app'
   ```

## Usage

1. Set your monthly budget on the dashboard
2. Tap the add button to record transactions
3. Choose categories for better organization
4. Monitor your spending against your budget

## Roadmap

- Charts and analytics for spending visualization
- Cloud sync with Supabase/Firebase
- Daily reminders and budget alerts
- Export reports (PDF/CSV)
- Recurring transactions
- Multi-currency support

   
## Author
**Shweta Mishra**  
GitHub: [@shwetamishra21](https://github.com/shwetamishra21)
---

Made with Kotlin and Jetpack Compose
