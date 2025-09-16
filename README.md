# Android Assessment - Countries List

## Overview
This project fetches a list of countries in JSON format from a remote URL and displays them in a RecyclerView using **Android Views (XML)**.  
It follows **MVVM architecture** with Repository, ViewModel, and LiveData.  

## Features
- Fetch countries JSON via Retrofit + Moshi
- Display in RecyclerView (ordered as in JSON)
- Each row shows:
  - Country Name + Region 
  - Code
  - Capital
- Error handling (loading, error states)
- Supports device rotation (ViewModel survives config changes)

## Screenshots
<img width="270" height="800" alt="Screenshot_20250916_110321" src="https://github.com/user-attachments/assets/1758e53e-e009-4e3c-9e66-562fd3b5431e" />


## Video Walkthrough
👉 [Watch the walkthrough video here](https://youtu.be/X1RHw5R_4Mo)

## Project Structure
- `data/` → Model, API, Repository
- `ui/` → Main UI, ViewModel, Adapter
- `state/` → UiState (Loading, Success, Error)
