# Linger

![image](https://github.com/trashplusplus/LinkGrabber/assets/19663951/56f80d99-0fc6-4260-ba66-45763aceab34)



**Linger** - a cleaning tool made for the CreatorIQ Cleanup Team. It includes a parser and additional utilities to monitor the cleaning process.

**Installation:**


1. Install JDK 17.0.5.
2. Download **Linger 0.5.1.zip**
2. Run java -jar %path%/Linger.jar in the command line.

**Usage Instructions:**

1. Select the "Search" tab and input the creator's username.
2. Five links from social media platforms will open in the browser.
3. If the parser finds TikTok or Instagram in the description, it may open an additional tab (or not).
4. To navigate tabs in the browser, use CTRL + TAB. To close the active tab, use CTRL + W.

Sometimes, the description and the number of followers cannot be parsed, for example, if the description is empty. If there's a "Parse Error," raw text may be added, containing visible information.

**Settings:**

1. Goal: Set the goal for creators, default is 20. If the limit is exceeded, add +5. Designed to simplify the tracking process.
2. Open Mode: Opens tabs in the browser. Enabled by default, can be disabled.
3. Clipboard Mode: Enabled by default. Automatically copies the TikTok link for easy pasting into CreatorIQ.

**Singer:**

Singer - a scanning mode that scrapes creators from a .txt and returns those whose accounts are not found in **singerOut.txt**

**Example:**

` java -jar Linger.jar -singer users.txt `

![image](https://github.com/trashplusplus/LinkGrabber/assets/19663951/3ec7d935-88ef-4cd3-9251-f43e9fffa45b)


