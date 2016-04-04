//
//  SecondViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    connected = false;
    [self refreshState];
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

- (void)refreshState {
    SWGEnvironmentApi * api = [[SWGEnvironmentApi alloc] init];
    
    [api temperatureSummaryWithCompletionHandler:^(SWGTemperatureSummary *output, NSError *error) {
        if(output) {
            [self makeHappy];
            NSArray<SWGTemperatueZoneStatus>* status = output.zoneStatus;
            for(SWGTemperatueZoneStatus * zoneStatus in status) {
                NSString * str = [zoneStatus _id];
                
                bool eq = [str isEqual:@"library"];
                if([[zoneStatus _id] isEqual:@"living-room" ]) {
                    self.label1.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"kitchen"]) {
                    self.label2.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"library"]) {
                    self.label3.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"kids-rooms"]) {
                    self.label4.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"dining-room"]) {
                    self.label5.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"master-bedroom"]) {
                    self.label6.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"master-bathroom"]) {
                    self.label7.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"basement-main"]) {
                    self.label8.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"exercise-room"]) {
                    self.label9.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"laundry-room"]) {
                    self.label10.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }
                else if([[zoneStatus _id] isEqual:@"office-guest-room"]) {
                    self.label11.text = [NSString stringWithFormat:@"%.1lfF", [[zoneStatus value] doubleValue ]];
                }

            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"living-room" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch1 setOn:YES animated:YES];
            }
            else {
                [self.switch1 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    [api getHeaterStateWithZoneId:@"kitchen" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch2 setOn:YES animated:YES];
            }
            else {
                [self.switch2 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"library" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch3 setOn:YES animated:YES];
            }
            else {
                [self.switch3 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"kids-rooms" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch4 setOn:YES animated:YES];
            }
            else {
                [self.switch4 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"dining-room" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch5 setOn:YES animated:YES];
            }
            else {
                [self.switch5 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"master-bedroom" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch6 setOn:YES animated:YES];
            }
            else {
                [self.switch6 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"master-bathroom" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch7 setOn:YES animated:YES];
            }
            else {
                [self.switch7 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"basement-main" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch8 setOn:YES animated:YES];
            }
            else {
                [self.switch8 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"exercise-room" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch9 setOn:YES animated:YES];
            }
            else {
                [self.switch9 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"laundry-room" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch10 setOn:YES animated:YES];
            }
            else {
                [self.switch10 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
    
    [api getHeaterStateWithZoneId:@"office-guest-room" completionHandler:^(SWGHeaterState *output, NSError *error) {
        if(output) {
            [self makeHappy];
            if([[output state] isEqual: @"on"]) {
                [self.switch11 setOn:YES animated:YES];
            }
            else {
                [self.switch11 setOn:NO animated:NO];
            }
        }
        else {
            [self makeSad];
        }
    }];
}

-(void) setHeaterStateForZone: (NSString*) zoneId
                        state: (NSString*) state {
    SWGEnvironmentApi * api = [[SWGEnvironmentApi alloc] init];

    [api setHeaterStateWithZoneId: zoneId
                            state: state
                completionHandler:^(SWGApiResponse *output, NSError *error) {
                    if(output) {
                        [self makeHappy];
                        NSLog(@"ok!");
                    }
                    else {
                        [self makeSad];
                    }
                }];

}


-(IBAction)switch1ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"living-room" state: state];
}

-(IBAction)switch2ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"kitchen" state: state];
}

-(IBAction)switch3ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"library" state: state];

}

-(IBAction)switch4ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"kids-rooms" state: state];

}

-(IBAction)switch5ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"dining-room" state: state];

}

-(IBAction)switch6ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"master-bedroom" state: state];
}

-(IBAction)switch7ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"master-bathroom" state: state];
}

-(IBAction)switch8ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"basement-main" state: state];
}

-(IBAction)switch9ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"exercise-room" state: state];

}

-(IBAction)switch10ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"laundry-room" state: state];
}

-(IBAction)switch11ValueChanged:(id)sender {
    NSString* state = NULL;
    if([((UISwitch*) sender) isOn]) {
        state = @"on";
    }
    else {
        state = @"off";
    }
    [self setHeaterStateForZone:@"office-guest-room" state: state];
}

-(IBAction) basementFloorTimer:(id)sender {
    /*
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    [api relayOnWithTimerWithCompletionBlock:@"basement" timer:@360 completionHandler:^(NSError *error) {
        if(error) {
            NSLog(@"%@", error);
            [self makeSad];
        }
        else {
            NSLog(@"ok!");
            [self makeHappy];
            [self refreshState];
        }
    }];
     */
}

-(IBAction) mainFloorTimer:(id)sender {
    /*
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    [api relayOnWithTimerWithCompletionBlock:@"middle-floor" timer:@360 completionHandler:^(NSError *error) {
        if(error) {
            NSLog(@"%@", error);
            [self makeSad];
        }
        else {
            NSLog(@"ok!");
            [self makeHappy];
            [self refreshState];
        }
    }];
     */
}

-(IBAction) secondFloorTimer:(id)sender {
    /*
    SWGPhidgetApi * api = [[SWGPhidgetApi alloc] init];
    
    [api relayOnWithTimerWithCompletionBlock:@"upstairs" timer:@360 completionHandler:^(NSError *error) {
        if(error) {
            NSLog(@"%@", error);
            [self makeSad];
        }
        else {
            NSLog(@"ok!");
            [self makeHappy];
            [self refreshState];
        }
    }];
     */
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
