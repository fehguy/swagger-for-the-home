//
//  FourthViewController.m
//  SFTHClient
//
//  Created by Tony Tam on 11/19/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import "FourthViewController.h"

@interface FourthViewController ()

@end

@implementation FourthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    connected = false;
    [self refreshState];
}

- (void)refreshState {
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

-(IBAction)muteMusic:(id)sender {
/*    SWGSonosApi* api = [[SWGSonosApi alloc] init];
    if([self.muteSwitch isOn] == TRUE) {
        [api resumeAllWithCompletionBlock:@0 completionHandler:^(NSError *error) {
            if(!error) {
                [self makeHappy];
                NSLog(@"ok!");
            }
            else {
                [self makeSad];
            }
        }];
    }
    else {
        [api pauseAllWithCompletionBlock:@0 completionHandler:^(NSError *error) {
            if(!error) {
                [self makeHappy];
                NSLog(@"ok!");
            }
            else {
                [self makeSad];
            }
        }];
    }
 */
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
