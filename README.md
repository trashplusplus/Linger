


**Linger** -  is a command-line utility designed to assist in CreatorIQ Data Cleanup by automating the process of opening all necessary links based on a specified username.

To use the software, firstly you should setup clipboard for your social network, (In case Tiktok - https://tiktok.com/ or https://instagram.com/ for Instagram) simply select "Search" and input the username. Linger will then open all required social media platforms, including TikTok, Instagram, YouTube, Twitter, and Facebook. For TikTok, a quick parsing method is always employed to determine whether the account corresponding to the given username was found or not. Additionally, an optional feature includes automatic age search on Famous Birthdays based on the name, if parsing the TikTok username is successful.

For unconventional methods of data cleanup (such as searching on social media platforms other than TikTok), users can set their own clipboard to ensure the correct link opens in CreatorIQ.

While it's possible to disable the automatic opening of links through settings, it's not recommended as it doesn't contribute to speeding up the database cleanup process in any way.

To bypass certain TikTok restrictions, users can utilize the "Use Cookie" parameter, where they can input their TikTok account's cookies.

In addition, if a Linktree or Beacons is detected, Linger automatically scrapes and opens additional links that are found and pass through the filter. This feature helps avoid third-party sites unrelated to the creator's social media networks.

![image](https://github.com/trashplusplus/Linger/assets/19663951/09dca858-0da7-4d63-a807-0b8c6a78fd2f)
![image](https://github.com/trashplusplus/Linger/assets/19663951/56e2a6c3-c116-4e82-a33a-111e3e712ff1)

**Installation:**


1. Install JDK 17.0.5.
2. Download **Linger 1.1.6.zip** from **Releases** on GitHub.

![image](https://github.com/trashplusplus/Linger/assets/19663951/9bc78aaf-9f69-4153-b019-98a9fc6877ff)

3. Run java -jar %path%/Linger.jar in the command line.

**Usage Instructions:**
1. Choose "Configure Clipboard" and enter the standard link for the social network you intend to work with. This link will subsequently be copied to your clipboard and automatically inserted into CreatorIQ.
2. Select the "Search" tab and input the creator's username.
3. Five links from social media platforms will open in the browser.
4. If the parser finds TikTok or Instagram in the description, it may open an additional tab (or not).
5. To navigate tabs in the browser, use CTRL + TAB. To close the active tab, use CTRL + W.

Sometimes, the description and the number of followers cannot be parsed, for example, if the description is empty. If there's a "Parse Error," raw text may be added, containing visible information.

**Settings:**

1. Goal: Set the goal for creators, default is 20. If the limit is exceeded, add +5. Designed to simplify the tracking process.
2. Open Mode: Opens tabs in the browser. Enabled by default, can be disabled.
3. Clipboard Mode: Enabled by default. Automatically copies the TikTok link for easy pasting into CreatorIQ.



