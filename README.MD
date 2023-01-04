# DBA (Discord Bot Annotation)

DBA is a JAVA library that allows you to easily create your commands using [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA).

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


## Getting started

### Defining a class as a command

To define a command, just use the `@Command` annotation above the desired Java class

```java
@Command(name = "help", description = "Displays the bot help.")
public class Help {
  //TODO
}
```

Two parameters are available in the annotation

| Parameter     |    Type    | Utility                                 | Required |
|---------------|:----------:|:----------------------------------------|:--------:|
| name          |   String   | The name we want to give to the command |   True   |
| description   |   String   | The description of the command          |   True   |

### Add options to the command

It is possible to add up to 25 options to the command (limited by JDA), thanks to the `@Option` annotation

```java
@Command(name = "ban", description = "Ban a member from the server.")
@Option(type = OptionType.USER, name = "member", description = "The member to ban.", required = true)
@Option(type = OptionType.STRING, name = "reason", description = "The reason for the ban.")
@Option(type = OptionType.STRING, name = "duration", description = "The duration for the ban.")
public class Ban {
  //TODO
}
```

The annotation receives up to 4 parameters

| Parameter     |    Type    | Utility                                     | Required |
|---------------|:----------:|:--------------------------------------------|:--------:|
| type          | OptionType | The JDA OptionType                          |   True   |
| name          |   String   | The option name                             |   True   |
| description   |   String   | The description of the option               |   True   |
| required      |  boolean   | If the option is mandatory (not by default) |  False   |


### Declare commands

Create a new instance of ClassLoader with a JDA instance as parameter.
You can then load the different commands on a guild or on Discord in general.

The example below represents the case where we want to load our commands on all the servers that our bot has joined.

```java
public class SetupCommands extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        new CommandLoader(event.getJDA()).loadGuildCommands(event.getGuild(),
                Ban.class,
                Help.class
                );        
    }
}
```

## Auto commands dispatch

It is possible to declare a method that runs automatically when the command linked to your class is called.

For this your class must `implement Custom Command` and must define a method `execute(SlashCommandInteractionEvent event)`

```java
@Command(name = "ban", description = "Ban a member from the server.")
@Option(type = OptionType.USER, name = "member", description = "The member to ban.", required = true)
@Option(type = OptionType.STRING, name = "reason", description = "The reason for the ban.")
@Option(type = OptionType.STRING, name = "duration", description = "The duration for the ban.")
public class Ban implements CustomCommand {

    public Ban(){
        //Required if a non-empty constructor is declared
    }
    
    public Ban(String s){
        //Example
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Example...").queue();
    }
}
```

**WARNING, your class must have an empty constructor. By default, if no constructor is defined, JAVA create one "invisible" by default. However if you define a constructor with parameters you must instantiate an empty one/**



## Contact us

If you have any questions, do not hesitate to contact us.

[![Discord](https://badgen.net/badge/icon/discord?icon=discord&label)](https://discord.gg/u7K4WcxMWS)

