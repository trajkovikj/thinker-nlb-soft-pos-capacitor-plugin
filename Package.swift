// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "ThinkerNlbSoftPos",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "ThinkerNlbSoftPos",
            targets: ["ThinkerNlbSoftPosPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "ThinkerNlbSoftPosPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/ThinkerNlbSoftPosPlugin"),
        .testTarget(
            name: "ThinkerNlbSoftPosPluginTests",
            dependencies: ["ThinkerNlbSoftPosPlugin"],
            path: "ios/Tests/ThinkerNlbSoftPosPluginTests")
    ]
)