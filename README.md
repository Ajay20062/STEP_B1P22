# STEP_B1P22

---

## ✅ COMPLETE & PROPER GIT CODE (STEP PROJECT)

### 1️⃣ Go to your project folder

```bash
cd ~/Documents/B1P22_RA2411003010071/STEP_B1P22_Week1_Week2
```

---

### 2️⃣ Initialize Git (only once)

```bash
git init
```

---

### 3️⃣ Check files

```bash
git status
```

---

### 4️⃣ Add all files

```bash
git add .
```

---

### 5️⃣ First commit

```bash
git commit -m "Initial commit - STEP Week1 and Week2"
```

---

### 6️⃣ Rename branch to main

```bash
git branch -M main
```

---

### 7️⃣ Add remote repository (HTTPS – recommended)

```bash
git remote add origin https://github.com/Ajay20062/STEP_B1P22.git
```

Verify:

```bash
git remote -v
```

---

### 8️⃣ Pull from remote (VERY IMPORTANT)

This avoids the **fetch first** error.

```bash
git pull origin main --allow-unrelated-histories
```

👉 If **Vim opens**, do:

```
Esc
:wq
Enter
```

---

### 9️⃣ Push to GitHub

```bash
git push -u origin main
```

✅ Your code is now live on **GitHub**

---

## 🔁 FUTURE WORKFLOW (After this setup)

Whenever you modify files:

```bash
git add .
git commit -m "Updated Week2 programs"
git push
```
