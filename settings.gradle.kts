include(
    ":app",
    ":characters",
    ":characters:public",
    ":characters:impl",
    ":favorites",
    ":favorites:public",
    ":favorites:impl",
    ":home:impl",
    ":network",
    ":network:public",
    ":network:internal",
    ":database",
    ":utils",
)
rootProject.name = "Marvel Wiki"
include(":navigation")
