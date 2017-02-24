#Setting The Site Up


I used Heroku to set up my Java app.
as described in the Heroku steps
# I Run this from your terminal, it will add the apt repository and install the CLI:
sudo add-apt-repository "deb https://cli-assets.heroku.com/branches/stable/apt ./"
curl -L https://cli-assets.heroku.com/apt/release.key | sudo apt-key add -
sudo apt-get update
sudo apt-get install heroku

# I Logged in to Heroku using command:
heroku login

# I cloned a sample application using command:
git clone https://github.com/heroku/java-getting-started.git


# I created a new repository on GitHub:
https://github.com/LariSami/Expedia

Since I hav cloned the application, I needed to change the origin's URL
So I used command:
	git remote set-url origin http://github.com/LariSami/Expedia
Then I pushed my project to my Expedia repositiry.