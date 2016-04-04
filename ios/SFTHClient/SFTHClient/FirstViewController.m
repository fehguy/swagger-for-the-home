//
//  FirstViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "FirstViewController.h"

@interface FirstViewController ()
@end

@implementation FirstViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    connected = false;
    [self refreshState];
    [self initBeacon];
}

- (void)initBeacon {
    NSUUID *beaconUUID = [[NSUUID alloc] initWithUUIDString:
                          @"98318B06-95FF-432B-BD70-5B291B55140C"];
    NSString *regionIdentifier = @"us.iBeaconModules";
    self.beaconRegion = [[CLBeaconRegion alloc]
                                    initWithProximityUUID:beaconUUID identifier:regionIdentifier];
    self.beaconRegion.notifyOnEntry = YES;
    self.beaconRegion.notifyOnExit = YES;
    
    self.locationManager = [[CLLocationManager alloc] init];
    // New iOS 8 request for Always Authorization, required for iBeacons to work!
    if([self.locationManager respondsToSelector:@selector(requestAlwaysAuthorization)]) {
        [self.locationManager requestAlwaysAuthorization];
    }
    self.locationManager.delegate = self;
    self.locationManager.pausesLocationUpdatesAutomatically = YES;
    
    [self.locationManager startMonitoringForRegion:self.beaconRegion];
    [self.locationManager startRangingBeaconsInRegion:self.beaconRegion];
    [self.locationManager startUpdatingLocation];
}

- (void)locationManager:(CLLocationManager *)manager didStartMonitoringForRegion:(CLRegion *)region {
    [self.locationManager startRangingBeaconsInRegion:self.beaconRegion];
}

-(void)peripheralManagerDidUpdateState:(CBPeripheralManager *)peripheral {
    if (peripheral.state == CBPeripheralManagerStatePoweredOn) {
        NSLog(@"Powered On");
        [self.peripheralManager startAdvertising:self.beaconPeripheralData];
    } else if (peripheral.state == CBPeripheralManagerStatePoweredOff) {
        NSLog(@"Powered Off");
        [self.peripheralManager stopAdvertising];
    }
}

-(void)sendLocalNotificationWithMessage:(NSString*)message {
    UILocalNotification *notification = [[UILocalNotification alloc] init];
    notification.alertBody = message;
    [[UIApplication sharedApplication] scheduleLocalNotification:notification];
}

-(void)locationManager:(CLLocationManager *)manager didRangeBeacons:(NSArray *)beacons inRegion:(CLBeaconRegion *)region {
    NSString *message = @"";
    
    if(beacons.count > 0) {
        CLBeacon *nearestBeacon = beacons.firstObject;
        switch(nearestBeacon.proximity) {
            case CLProximityFar:
                message = @"You are far away from the beacon";
                break;
            case CLProximityNear:
                message = @"You are near the beacon";
                break;
            case CLProximityImmediate:
                message = @"You are in the immediate proximity of the beacon";
//                [self sendLocalNotificationWithMessage:message];
                break;
            case CLProximityUnknown:
                return;
        }
    } else {
        message = @"No beacons are nearby";
    }
    
    NSLog(@"%@", message);
}

- (void) locationManager:(CLLocationManager *)manager didEnterRegion:(CLRegion *)region {
    NSString *message = @"you are home!";
    NSLog(@"%@", message);

    [self sendLocalNotificationWithMessage:message];
}

- (void) locationManager:(CLLocationManager *)manager didExitRegion:(CLRegion *)region {
    NSString *message = @"see you later!";
    NSLog(@"%@", message);
    [self sendLocalNotificationWithMessage:message];
}

- (void)makeHappy {
    UIImage* image = [UIImage imageNamed:@"logo_swagger"];
    [self.logoButton setImage:image forState:UIControlStateNormal];
    [self.logoButton setNeedsLayout];

    connected = true;
}

- (void)makeSad {
    UIImage* image = [UIImage imageNamed:@"logo_sad_swagger"];
    [self.logoButton setImage:image forState:UIControlStateNormal];
    [self.logoButton setNeedsLayout];

    connected = false;
}

- (void) setSwitchForZone: (UISwitch*) sw
                      state: (NSNumber*) state {
    if([state integerValue] == 0) {
        [sw setOn:NO animated:YES];
    }
    else {
        [sw setOn:YES animated:YES];
    }
}

- (void) setSwitchStateForZone: (NSString*) zoneId
                         state: (NSNumber*) numberState {
    SWGZWaveApi * api = [[SWGZWaveApi alloc] init];
    
    NSString * state = @"on";
    if([numberState intValue] == 0) {
        state = @"off";
    }
    [api setSwitchWithDeviceId:zoneId
                         value: state
             completionHandler:^(SWGApiResponse *output, NSError *error) {
        if(output) {
            [self makeHappy];
        }
    }];
}

- (void) setDimmerStateForZone: (NSString*) zoneId
                         state: (NSNumber*) state {
    SWGZWaveApi * api = [[SWGZWaveApi alloc] init];
    
    [api setDimmerWithDeviceId: zoneId
                         value: state
             completionHandler:^(SWGApiResponse *output, NSError *error) {
                 if(output) {
                     [self makeHappy];
                 }
             }];
}

- (void)refreshState {
    [self makeSad];
    
    SWGZWaveApi * api = [[SWGZWaveApi alloc] init];
    
    [api getLightingSummaryWithCompletionHandler:^(SWGLightingSummary *output, NSError *error) {
        if(output) {
            [self makeHappy];
            for(SWGLightingZoneStatus * zone in output.zoneStatus) {
                if([[zone _id] isEqual:@"christmas-tree-light"]) {
                    [self setSwitchForZone: self.switch5 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"living-room-table-light"]) {
                    [self setSwitchForZone: self.switch2 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"living-room-bay-window"]) {
                    [self setSwitchForZone: self.switch6 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"bedroom-hallway"]) {
                    [self setSwitchForZone: self.switch2 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"office-light"]) {
                    [self setSwitchForZone: self.switch4 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"basement-main"]) {
                    [self setSwitchForZone: self.switch3 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"basement-bathroom"]) {
                    [self setSwitchForZone: self.switch7 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"fishtank-light"]) {
                    [self setSwitchForZone: self.switch8 state:[zone level]];
                }
                else if([[zone _id] isEqual:@"patio-lights"]) {
                    [self setSwitchForZone: self.switch9 state:[zone level]];
                }
            }
        }
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction) switch1ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;

    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setSwitchStateForZone: @"living-room-table-light"
                          state: [[NSNumber alloc] initWithInt: state]];
}

-(IBAction) switch1Timer:(id)sender {
    [self.switch1 setOn:YES animated:YES];
/*
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
    [api setSwitchValueWithTimerWithCompletionBlock:@6 timer:@15 completionHandler:^(NSError *error) {
        if(error) {
            NSLog(@"%@", error);
            [self makeSad];
        }
        else {
            NSLog(@"ok!");
            [self makeHappy];
        }
    }];
 */
}

-(IBAction) switch2ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;

    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setDimmerStateForZone: @"bathroom-hallway"
                          state: [NSNumber numberWithInt: state]];
}

-(IBAction) switch2Timer:(id)sender {
    [self.switch2 setOn:YES animated:YES];
    /*
    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];

    [api dimmerOnWithTimerWithCompletionBlock:@8 timer:@15 completionHandler:^(NSError *error) {
        if(error) {
            NSLog(@"%@", error);
            [self makeSad];
        }
        else {
            NSLog(@"ok!");
            [self makeHappy];
        }
    }];
     */
}


-(IBAction) switch3ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setDimmerStateForZone: @"basement-main"
                          state: [[NSNumber alloc] initWithInt: state]];
}

-(IBAction) switch3Timer:(id)sender {
    [self.switch2 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api dimmerOnWithTimerWithCompletionBlock:@4 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch4ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setDimmerStateForZone: @"office-light"
                          state: [[NSNumber alloc] initWithInt: state]];

}

-(IBAction) switch4Timer:(id)sender {
    [self.switch4 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api dimmerOnWithTimerWithCompletionBlock:@3 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch5ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 255;
    }
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    [api setSwitchValueWithCompletionBlock:@2 value:[[NSNumber alloc] initWithInt:state] completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch5Timer:(id)sender {
    [self.switch4 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api setSwitchValueWithTimerWithCompletionBlock:@2 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch6ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setDimmerStateForZone: @"living-room-bay-window"
                          state: [[NSNumber alloc] initWithInt: state]];
}

-(IBAction) switch6Timer:(id)sender {
    [self.switch6 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api dimmerOnWithTimerWithCompletionBlock:@1 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch7ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setDimmerStateForZone: @"basement-bathroom"
                          state: [[NSNumber alloc] initWithInt: state]];
}

-(IBAction) switch7Timer:(id)sender {
    [self.switch7 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api dimmerOnWithTimerWithCompletionBlock:@11 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

-(IBAction) switch8ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;

    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setSwitchStateForZone: @"fishtank-light" state: [NSNumber numberWithInt: state]];
}

-(IBAction) switch8Timer:(id)sender {
    [self.switch8 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api setSwitchValueWithTimerWithCompletionBlock:@5 timer:@15 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}
    
-(IBAction) switch9ValueChanged:(id)sender {
    UISwitch* l_switch = (UISwitch*) sender;
    
    int state = 0;
    if([l_switch isOn]) {
        state = 99;
    }
    [self setSwitchStateForZone: @"patio-lights"
                          state: [[NSNumber alloc] initWithInt: state]];
}

-(IBAction) switch9Timer:(id)sender {
    [self.switch9 setOn:YES animated:YES];
    
//    SWGZwaveApi * api = [[SWGZwaveApi alloc] init];
//    
//    [api setSwitchValueWithTimerWithCompletionBlock:@20 timer:@20 completionHandler:^(NSError *error) {
//        if(error) {
//            NSLog(@"%@", error);
//            [self makeSad];
//        }
//        else {
//            NSLog(@"ok!");
//            [self makeHappy];
//        }
//    }];
}

@end
