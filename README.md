# STEP_B1P22

GIT ACCOUNT MANAGEMENT

If the previous user did not log out properly, follow ALL steps below before starting work.

🟢 STEP 1 — Logout from GitHub (Browser)

Open github.com

Click profile photo (top right)

Click Sign out

Why?
To prevent creating repositories or managing projects under the wrong account.

This step only affects browser login, not Git push access.

🟢 STEP 2 — Remove Saved Git Credentials (System Level)

Git stores login details in Windows Credential Manager.
If not removed, the next student will push code to the previous student’s account.

Go to:

Control Panel → Credential Manager → Windows Credentials

Find entries like:

git:https://github.com


Remove ALL GitHub-related entries.

This ensures Git asks for fresh login credentials during the next push.

🟢 STEP 3 — Verify Local Git Identity

Inside the project folder, run:

git config user.name
git config user.email


If incorrect, set them properly:

git config user.name "Student Name"
git config user.email "student@email.com"


Important:
This only controls the commit author name and email.
It does NOT control GitHub login access.

🟢 STEP 4 — Push and Login with Correct Account

Run:

git push
