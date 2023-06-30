# DBA (Discord Bot Annotation)

## Introduction

This library work with [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA) !

The DBA library makes it very easy to create and use components linked to events (command / button / selectMenu / modal) with the annotation system in java. In addition, it allows a cleaner code structure, in fact a class is equivalent to a component and what it must do when it is called.
No need to call the same events several times for different actions in your code, or to create a huge switch().

## Installation

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation ('com.github.Leonarddoo:DiscordBotAnnotation:VERSION')
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.Leonarddoo</groupId>
    <artifactId>DiscordBotAnnotation</artifactId>
    <version>VERSION</version>
</dependency>
```



## List of components

### @Command

| Parameter     |    Type    | Utility                                 | Required |
|---------------|:----------:|:----------------------------------------|:--------:|
| name          |   String   | The name we want to give to the command |   True   |
| description   |   String   | The description of the command          |   True   |

### @Option

| Parameter     |    Type    | Utility                                     | Required |
|---------------|:----------:|:--------------------------------------------|:--------:|
| type          | OptionType | The JDA OptionType                          |   True   |
| name          |   String   | The option name                             |   True   |
| description   |   String   | The description of the option               |   True   |
| required      |  boolean   | If the option is mandatory (not by default) |  False   |

### @Button

| Parameter     |    Type    | Utility                  | Required |
|---------------|:----------:|:-------------------------|:--------:|
| id            |   String   | The ID of the button     |   True   |

### @SelectMenu

| Parameter     |    Type    | Utility                  | Required |
|---------------|:----------:|:-------------------------|:--------:|
| id            |   String   | The ID of the selectMenu |   True   |

### @Modal

| Parameter     |    Type    | Utility                  | Required |
|---------------|:----------:|:-------------------------|:--------:|
| id            |   String   | The ID of the modal      |   True   |


## Getting started

### Declaration of targeted classes

First, create a new instance of `Loader`. This instance has a `load()` method which takes a guild or a JDA instance as a parameter as well as a list of classes.
The choice of the first concerns only the accessibility of the commands. The JDA instance allows you to load them everywhere (even in DM with the bot), the guild instance to load them only on the guild.

In the following example we will create :  
- A **/welcome** command which will have an optional @user. This command will respond "Welcome {user.name} to {server.name}".
- A **button** with the id "ping" which responds "pong" when clicked.

```java
public class Setup extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Loader().load(event.getGuild(),
                new WelcomeCommand(),
                new PingButton()
        );
    }
}
```

### Create Command class

```java
@Command(name = "welcome", description = "Send a welcome message to a member.")
@Option(type = OptionType.USER, name = "user", description = "The new member", required = true)
public class WelcomeCommand implements DBACommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        event.reply("Welcome " + user.getName() + " to " + event.getGuild().getName()).queue();
    }
}
```

Remember to have indicated your class in the Loader, to have put the annotation to your class and that it implements the right class, `CustomCommand` for a Command.

### Create Button class

```java
@Button(id = "ping")
public class PingButton implements DBAButton {
    @Override
    public void execute(ButtonInteractionEvent event) {
        event.reply("Pong").queue();
    }
}
```

Again, the class must be in the loader, have the annotion (@Button for the example) and implement `CustomButton`.

## Finally 

You can do the same for `SelectMenu` and `Modal`. I remain open to any suggestions to improve the current version.

If you have any questions, I remain available.

## Contact us

[![Discord](https://badgen.net/badge/icon/discord?icon=discord&label)](https://discord.gg/a8QS7Jhs6G)
https://botdiscord.fr

